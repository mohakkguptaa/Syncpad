# Content Sync — Backend + WebSocket

This repository is a hands-on practice that demonstrates the basic idea behind content syncing, desyncing, and real-time updates using APIs + WebSockets.  
It is intentionally minimal, meant as a sandbox environment to understand the core flows, not a full product implementation.

---

## What’s Included

- SQL script for **Content** and **Location** tables  
- Spring Boot backend with APIs:
  - Create  
  - Update  
  - Sync  
  - Unsync  
- A simple `ws-test.html` file to manually test WebSocket connections and channel subscriptions

---

## How to Run This Project

### 1. Database Setup
1. Create a new database.  
2. Use the SQL script in the `/db` folder for creating the two required tables:
- `content`
- `location`

---

### 2. Run the Spring Boot Project
Start the application normally.  
All APIs will be accessible from the exposed endpoints.

---

## 3. Test the Core Flows (Recommended Order)

### **A. Create Content**
Call the **create** API with:
- `body`
- `locationId`

The response will contain a new `contentId` and version `1`.

---

### **B. Sync**
Use the **sync** API to map a new location to an existing `contentId`.

---

### **C. Update**
Call the **update** API on a `contentId`.

- Version increments  
- All synced locations linked to that content should receive the updated content
  
---

### **D. Desync**
Use the **desync** API to:
- Create a fresh copy of the existing content  
- Reset version to `1`  
- Map the location to this new independent content

---

## 4. WebSocket Testing (`ws-test.html`)

The provided `ws-test.html` is a minimal WebSocket tester used to demonstrate real-time updates.

It includes:
- Input for WebSocket URL (your Spring Boot WS endpoint)
- Field to enter the channel/topic name
- Buttons for **Connect** and **Subscribe**

### How to Use It
1. Open `ws-test.html` in your browser.  
2. Enter your WebSocket URL in the HTML file.  
3. Connect.  
4. Subscribe to a content update channel (configure the channel based on the update method in service layer).  
5. Call the **update** API for that contentId.  

You should instantly see the updated content appear in the HTML page.

This demonstrates:
- Backend broadcasting updates to the subscribed channel  
- Client receiving updates in real time without calling any API  
