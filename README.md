# Flag_Camp_Landuary

put a valid email address and password in EmailUtils.java

Machine state:
0 - vacant
1 - booked
2 - in use
3 - finish, waiting for pickup
4 - idle timeout

Current web service call:
prefix = “localhost:8080/Laundry”

POST   /register
{
  “user_id”: “demo”,
  “password”: “111”,
  “first_name”: “Sharon”,
  “last_name”: “Xia”,
  “email_address”: “example@gmail.com”
}

response:
{
  “status” : “OK” / “User Already Exists”
}

POST /login                                           GET  /login
{
  “user_id”: “demo”,
  “password”: “111”
}

response:
{
 “status”: “OK” / “User Doesn’t Exist” / “Invalid Session”,
  “user_id”: “demo”,
  “name”: “Sharon Xia”,
  “is_admin”: false
}

GET /logout

GET /allMachineStatus

response:
{
   {
       "machineId": 10,
       "state": 1,
       "timeLeft": 19
   },
   {
       "machineId": 5,
       "state": 0
   }
}

GET /bookLaundry?machineId=5
response:
403 didn’t login
{
   "status": "OK" / “Machine occupied”
}
 
GET /startWashing?machineId=5
response:
403 didn’t login
{
  “status”: “OK” / “Can not start”
}

GET /getMyMachineStatus
response:
403 didn’t login
{
   {
       "machineId": 5,
       "state": 1,
       "timeLeft": 59
   }
}
 
GET /pickupClothes?machineId=5
response:
403 didn’t login
{
   "status": "OK" / “Invalid pickup”
}
 
GET /addMachine?machineId=3
response:
401 not an admin
403 didn’t login
{
  “status”: “OK” / “Add Failed”
}
 
GET /deleteMachine?machineId=3
response:
401 not an admin
403 didn’t login
{
  “status”: “OK” / “Delete Failed”
}
  
Current 
booking time: 20mins
washing time: 60s
idle time: 30s
