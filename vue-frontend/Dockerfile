# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# production stage
FROM node:lts-alpine as production-stage
WORKDIR /app
COPY --from=build-stage /app/dist /app/dist
RUN npm install -g serve
ENV PORT=80
EXPOSE 80
CMD ["serve", "-s", "dist", "-l", "3000"]