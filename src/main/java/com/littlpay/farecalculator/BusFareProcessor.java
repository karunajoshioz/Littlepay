package com.littlpay.farecalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.littlpay.farecalculator.csv.util.CSVUtility;

@Service
public class BusFareProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(BusFareProcessor.class);

	public void readInputTaps() {

		List<BusFare> busFareList = new ArrayList<>();		
		List<BusFee> resultantList = new ArrayList<>();
		LOGGER.debug("Loading taps.csv");
		
		Set<String> calculatedIndexSet = new  HashSet<>();

		try 
		{
			List<String[]> allRows = CSVUtility.readCSV("taps.csv");
			if(allRows != null && !allRows.isEmpty()) {
				busFareList = CSVUtility.processCSVData(allRows);
			}
						
			//sort the list datewise
			Collections.sort(busFareList, new Comparator<BusFare>() {
                public int compare(final BusFare object1, final BusFare object2) {
                    return object1.getDateTime().compareTo(object2.getDateTime());
                }
            });
			
			//calculate fare per group
			if(busFareList != null && !busFareList.isEmpty()) 
			{
				boolean hangingTxn = false;
				for (BusFare bus : busFareList) 
				{					
					BusFee resultBusFare = new BusFee();
					
					String pannNo = bus.getPanNo().trim();
					String panTapType = bus.getTapType().trim();
					Date taponTime = bus.getDateTime();
					
					if("ON".equalsIgnoreCase(panTapType) ) 
					{
						for (BusFare bus1 : busFareList) 
						{							
							if(bus1.getPanNo().trim().equals(pannNo) && "OFF".equals(bus1.getTapType().trim())
									&& (bus1.getDateTime().compareTo(taponTime)) > 0) 
							{								
								resultBusFare = storeResultantData(bus1, taponTime, pannNo, bus);
								
								//cal status				
								if(!bus.getStopId().equalsIgnoreCase(bus1.getStopId())) {
									resultBusFare.setStatus("COMPLETED");
									//cal charge									
									Double fees = getFees(bus.getStopId(), bus1.getStopId());
									resultBusFare.setChargeAmount(fees);
								}
								else {
									resultBusFare.setStatus("CANCELLED");
									resultBusFare.setChargeAmount(new Double(0));
								}
								
								hangingTxn = false;
								resultantList.add(resultBusFare);
								calculatedIndexSet.add(bus.getId());
								calculatedIndexSet.add(bus1.getId());
								break;
							}//end of inner if
							hangingTxn = true;
						}//end of second for loop
						
						if(hangingTxn) {
							//incomplete trip
							if(!calculatedIndexSet.contains(bus.getId()))
							{
								resultBusFare.setStartDate(taponTime);
								resultBusFare.setEndDate(bus.getDateTime());													
								resultBusFare.setDuration(Long.parseLong("0"));								
								resultBusFare.setFromStopId(bus.getStopId());
								resultBusFare.setEndStopId(bus.getStopId());
								resultBusFare.setCompanyId(bus.getCompanyId());
								resultBusFare.setBusID(bus.getBusID());
								resultBusFare.setPanNo(pannNo);
								
								resultBusFare.setStatus("INCOMPLETE");
								//cal charge									
								resultBusFare.setChargeAmount(getMaxCharge(bus.getStopId()));						
								calculatedIndexSet.add(bus.getId());
								resultantList.add(resultBusFare);
							}
						}
					}// end of first if
					
				}//end of for
					
			}
			
			
			///wriet to cvs			
			CSVUtility.writeDataToCsv(resultantList);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	private BusFee storeResultantData(BusFare bus1, Date taponTime, String pannNo, BusFare orgBus)
	{
		BusFee resultBusFee = new BusFee();
		
		resultBusFee.setStartDate(taponTime);
		resultBusFee.setEndDate(bus1.getDateTime());
		
		long secondsBetween = (bus1.getDateTime().getTime() - taponTime.getTime()) / 1000;								
		resultBusFee.setDuration(secondsBetween);
		
		resultBusFee.setFromStopId(orgBus.getStopId());
		resultBusFee.setEndStopId(bus1.getStopId());
		resultBusFee.setCompanyId(bus1.getCompanyId());
		resultBusFee.setBusID(bus1.getBusID());
		resultBusFee.setPanNo(pannNo);
		
		return resultBusFee;
	}
	
	/**
	 * 
	 * @param stop1
	 * @param stop2
	 * @return
	 */
	private Double getFees(String stop1, String stop2)
	{
		Double fees = 0.0;
	    for(Charges charges : Charges.values())
	    {
		   if(stop1.equalsIgnoreCase(charges.getStop1()) && stop2.equalsIgnoreCase(charges.getStop2())) 
			   fees = Double.parseDouble(charges.getFee());
	    }
	    return fees;
	}	
	
	/**
	 * 
	 * @param stopId
	 * @return
	 */
	private Double getMaxCharge(String stopId)
	{
		Double fees = 0.0;
		List<Double> chargesList = new ArrayList<>();
		
	    for(Charges charges : Charges.values())
	    {
		   if(stopId.equalsIgnoreCase(charges.getStop1())) 
			   chargesList.add(Double.parseDouble(charges.getFee()));
	    }
	    fees = Collections.max(chargesList);
	    return fees;
	}

}
