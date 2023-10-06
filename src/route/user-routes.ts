import express, { Request, Response, Router } from "express";
import * as userController from "../controller/user-controller";
import { handleValidationErrors } from "../middleware/validation-middleware";
import { userValidation } from "../utility/user-validation";

const router: Router = express.Router();

router.get("/", (req: Request, res: Response) => {
  userController.getUsers(req, res);
});

router.get("/:id", (req: Request, res: Response) => {
  userController.getUser(req, res);
});

router.post(
  "/",
  userValidation(),
  handleValidationErrors,
  (req: Request, res: Response) => {
    userController.createUser(req, res);
  }
);

router.put(
  "/:id",
  userValidation(),
  handleValidationErrors,
  (req: Request, res: Response) => {
    userController.updateUser(req, res);
  }
);

router.delete("/:id", (req: Request, res: Response) => {
  userController.deleteUser(req, res);
});

export default router;
