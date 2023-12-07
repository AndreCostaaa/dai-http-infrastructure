# Front end

- For this project, we'll create a simple react-app using vite

- The setup process is really easy

## React

Node version used v20.6.1
Npm version used 9.8.1

Step 1) Create the vite app and follow the instructions

```
npm create vite@latest
```

We setup a react project using type-script

Step 2) Install dependencies

```
cd garage-frontend
npm install
```

## Nginx

Step 3) Create the nginx config

We need to tell nginx 2 things:

1. The port to listen to (Port 80 in our case)

2. Where to find the our compiled react project

```{nginx}
server {
    listen       80;
    root   /usr/share/nginx/html;
}
```

## Docker

Step 3) Create the Docker file

The file has to:

1. Use nginx as its base image
2. Copy the nginx config file
3. Copy the dist folder that contains our html, css and js
4. Remove the default config file

```{Docker}
FROM nginx

COPY ./dist /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx/nginx.conf /etc/nginx/conf.d/nginx.conf
```

## Building and deploying

```
npm run build
docker build . -t garage/front-end
docker run -p 5111:80 garage/front-end
```

## Result

![Result Step 1](../media/frontend-deployed.png)
