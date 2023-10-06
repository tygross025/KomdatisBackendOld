import { Request, Response } from "express";
import UserModel, { User } from "../model/user";

export const getUsers = async (req: Request, res: Response): Promise<void> => {
  try {
    const users: User[] = await UserModel.find({});
    res.json(users);
  } catch (err) {
    res.json(err);
  }
};

export const getUser = async (req: Request, res: Response): Promise<void> => {
  const id: string = req.params.id;
  try {
    const user: User | null = await UserModel.findById({ _id: id });
    res.json(user);
  } catch (err) {
    res.json(err);
  }
};

export const createUser = async (
  req: Request,
  res: Response
): Promise<void> => {
  try {
    const user: User = await UserModel.create(req.body);
    res.json(user);
  } catch (err) {
    res.json(err);
  }
};

export const updateUser = async (
  req: Request,
  res: Response
): Promise<void> => {
  const id: string = req.params.id;
  try {
    const user: User | null = await UserModel.findByIdAndUpdate(
      { _id: id },
      {
        name: req.body.name,
        email: req.body.email,
        age: req.body.age,
      }
    );
    res.json(user);
  } catch (err) {
    res.json(err);
  }
};

export const deleteUser = async (
  req: Request,
  res: Response
): Promise<void> => {
  const id: string = req.params.id;
  try {
    const response: User | null = await UserModel.findByIdAndDelete({
      _id: id,
    });
    res.json(response);
  } catch (err) {
    res.json(err);
  }
};
