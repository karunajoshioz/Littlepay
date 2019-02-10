#Problem Statement

The problem
Given an input file in CSV format containing a single tap on or tap off per line you will need to create an output
file containing trips made by customers.
taps.csv [input file]
You are welcome to assume that the input file is well formed and is not missing data.
Example input file:
ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN
1, 22-01-2018 13:00:00, ON, Stop1, Company1, Bus37, 5500005555555559
2, 22-01-2018 13:05:00, OFF, Stop2, Company1, Bus37, 5500005555555559
trips.csv [output file]
You will need to match the tap ons and tap offs to create trips. You will need to determine how much to
charge for the trip based on whether it was complete, incomplete or cancelled and where the tap on and tap
off occurred. You will needed to write the trips out to a file called trips.csv.
Example output file:
Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN,
Status
22-01-2018 13:00:00, 22-01-2018



#Solution

1. Written a spring boot application to read the taps.csv file
2. Creates trips.csv with calculated fare


# Skipped
Due to time constraints following scenarios are skipped
1. taps.csv contains only sequential data
2. Jumbled or mixed bus trips not given
3. Missing records for multiple panno not considered
4. Performance and Coding standards not followed.

