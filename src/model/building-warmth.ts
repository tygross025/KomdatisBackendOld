import mongoose, { Document, Schema } from "mongoose";

export interface BuildingWarmth extends Document {
  buildingId: mongoose.Types.ObjectId;
  value: number;
}

export const BuildingWarmthSchema: Schema = new Schema({
  buildingId: { type: mongoose.Types.ObjectId, ref: "Building" },
  value: Number,
});
