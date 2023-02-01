# Authorization code flow example

---

**Q: What is redirect URL?**  
Callback URL is a URL to which Authorization Server will redirect user after the authorization.
It is also a way for the Authorization Server to pass sensitive information back to the application -
e.g. in authorization code flow the code that has to be exchanged for an OAuth token is passed as a
parameter of callback URL.

**Q: What are entities involved in Authorization code flow and what are their tasks?**  
* **Resource Owner** - it's, as the name suggest, a user or a system that owns the resources -
in this case it's the registered user. Resource Owner can grant access to the owned resources
which is its role in authorization code flow.
* **Client Application** - client is the system that wants to access protected resources. To do so,
client needs to hold appropriate access token. In this case the application itself is a client,
as it needs resource owner data to show it on home page.
* **Resource Server** - it's a server responsible for protecting and serving user's resources. Its role
is to validate access token and return resources to the asking client if appropriate.
* **Authorization Server** - this is the server that receives a request from the client for access token.
It issues that token after client's successful authorization and receiving resource owner consent. In
this demo app Auth0 is the Authorization Server.

**The Authorization code flow diagram:**  
![Authorization Code Flow diagram](https://images.ctfassets.net/cdy7uua7fh8z/2nbNztohyR7uMcZmnUt0VU/2c017d2a2a2cdd80f097554d33ff72dd/auth-sequence-auth-code.png)
