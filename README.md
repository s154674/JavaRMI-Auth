# Java Authentication RMI
**Authors** Andreas Soelberg - s165206, Johan Hagelskjær Sjursen - s154674, Jia Hao Johnny Chen - s165543Jonathan Yngve Friis - s165213

This application is develop as part of the DTU course 02239 Data Security Autumn 2020

prerequisite:
- JAVA JDK 15
- JAVA compatible IDE e.g. Visual studiio code

To run the app:
1. Open source code in an IDE
2. Start ServerOps.java (Server application)
  - If successful it will print: "PrintServer bound to registry on port 2020"
    **NOTE** Because the servere read/write to the users.txt file, the application can be blocked by the security on the system.
3. Start ClientOps.java (Client application)
4. Inter a valid combination of username/password which can be found below.

Users:
("Alice","al123")
("Bob","bo123")
("Connie","co123")
("Ahmed","lars2020")
("Admin","admin1234")
("Johnny","FitnessIsMyPassion2020")
("Jonathan","ViSesDrenge#PH")
("John","Doe#¤%&92kjGH03")
