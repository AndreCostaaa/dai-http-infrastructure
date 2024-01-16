FROM node:21 AS builder

WORKDIR /app

COPY package*.json ./ 

RUN npm install

COPY . .

RUN npm run build

FROM nginx:1.25.3

COPY --from=builder /app/dist /usr/share/nginx/html

RUN rm /etc/nginx/conf.d/default.conf

COPY ./nginx/nginx.conf /etc/nginx/conf.d/nginx.conf