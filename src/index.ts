import cors from "cors";
import express from "express";
import "./config/db";
import userRoutes from "./route/user-routes";

const app = express();
app.use(cors());
app.use(express.json());

app.use("/users", userRoutes);

app.listen(3001, () => {
  console.log("Server is running");
});
