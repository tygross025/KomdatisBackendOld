import mongoose, { Schema, Document, Model } from "mongoose";

export interface User extends Document {
  name: string;
  email: string;
  age: number;
}

const UserSchema: Schema = new Schema({
  name: String,
  email: String,
  age: Number,
});

const UserModel: Model<User> = mongoose.model<User>("users", UserSchema);

export default UserModel;
