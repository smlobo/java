# Spring REST Service with Jetty

## Endpoints
1. `/`
2. `/comedy`
3. `/drama`
4. `/horror`
5. `/mystery`
6. `/thriller`

## JSON
```
% curl http://localhost:8088
% curl -H "Accept: application/json" http://localhost:8088/mystery
```

## XML
```
% curl -H "Accept: application/xml" http://localhost:8088/horror
```
