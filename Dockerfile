# Specify Base Image
FROM node:alpine

WORKDIR /usr/app

# Install Dependency
COPY ./package.json ./
RUN npm install
COPY ./index.js ./
COPY ./src ./

# Run Start Command
CMD ["npm", "start"]