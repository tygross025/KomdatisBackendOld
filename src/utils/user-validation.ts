import { body, ValidationChain } from "express-validator";

export const userValidation = (): ValidationChain[] => {
  return [
    // Name Validation
    body("name")
      .notEmpty()
      .isLength({ min: 3, max: 20 })
      .withMessage("Name must be between 3 and 20 characters"),
    // Address Validation
    body("email")
      .notEmpty()
      .isLength({ min: 3, max: 100 })
      .withMessage("Email must be between 3 and 20 characters"),
    // Age Validation
    body("age")
      .notEmpty()
      .isLength({ min: 1 })
      .withMessage("Age must be at least 1 character"),
  ];
};
