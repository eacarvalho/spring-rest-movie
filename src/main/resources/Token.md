https://spring.io/guides/tutorials/spring-boot-oauth2/

curl -u fish:memory http://10.0.82.13:8080/oauth/token -d grant_type=client_credentials
curl -u fish:memory http://10.0.82.13:8080/oauth/token -d grant_type=password -d username=user -d password=password
curl -u fish:memory http://10.0.82.13:8080/oauth/token -d "grant_type=refresh_token&refresh_token=f1445746-b240-4dfd-9b19-f912aad9cb05

curl -u fish:memory http://10.0.82.13:8080/oauth/token -d grant_type=client_credentials
curl -x POST http://10.0.82.13:8080/user -d '{"username":"abc", "password":"abc", "authorities":["ROLE_ADMIN"], "extensions": {"nome":"Administrador Abc" }}' -H "Authorization: Bearer eeef3173-58b5-4263-9d9a-e30aa56b2a50" -H "Content-Type: application/json"

{"access_token":"370592fd-b9f8-452d-816a-4fd5c6b4b8a6","token_type":"bearer","expires_in":43199,"scope":"read write"}
{"access_token":"aa49e025-c4fe-4892-86af-15af2e6b72a2","token_type":"bearer","refresh_token":"97a9f978-7aad-4af7-9329-78ff2ce9962d","expires_in":43199,"scope":"read write"}