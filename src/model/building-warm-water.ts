import mongoose, { Document, Schema } from "mongoose";

export interface BuildingWarmWater extends Document {
  buildingId: mongoose.Types.ObjectId;
  value: number;
}

export const BuildingWarmWaterSchema: Schema = new Schema({
  buildingId: { type: mongoose.Types.ObjectId, ref: "Building" },
  value: Number,
});
