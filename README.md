# dai-http-infrastructure

# Implementation

## Docker Compose

### FrontEnd

1. We start by defining the container-name and the image-name
2. For the building we can define a build directive, we set our context to the frontend folder and indicate the name of the dockerfile
3. Last but not least, we define the ports so that we can see the browser. This step is provisory, at the end of our implementation, we'll use the docker compose network directly and there won't be a need to expose any ports.

# Result

![](media/compose-build.png)

![](media/result-compose.png)
