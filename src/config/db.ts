import mongoose from "mongoose";
import dotenv from "dotenv";

dotenv.config();

const connectionString: string = process.env.MONGO_DB_URI || "";
mongoose
  .connect(connectionString)
  .then(() => {
    console.log("Connected to the Database");
  })
  .catch((error) => {
    console.log("Can not connect to the Database", error);
  });
