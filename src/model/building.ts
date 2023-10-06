import mongoose, { Document, Model, Schema, Types } from "mongoose";
import { BuildingWarmWater } from "./building-warm-water";
import { BuildingWarmth } from "./building-warmth";

export interface Building extends Document {
  firstName: string;
  lastName: string;
  address: string;
  livingSpace: number;
  warmth: Types.Array<Document & BuildingWarmth>;
  warmWater: Types.Array<Document & BuildingWarmWater>;
  heatedBasement: boolean;
  apartments: number;
}

const BuildingSchema: Schema = new Schema({
  firstName: { type: String, required: true },
  lastName: { type: String, required: true },
  address: { type: String, required: true },
  livingSpace: { type: Number, required: true },
  warmth: [{ type: Types.ObjectId, ref: "BuildingWarmth" }],
  warmWater: [{ type: Types.ObjectId, ref: "BuildingWarmWater" }],
  heatedBasement: { type: Boolean, required: true },
  apartments: { type: Number, required: true },
});

const BuildingModel: Model<Building> = mongoose.model<Building>(
  "buildings",
  BuildingSchema
);

export default BuildingModel;
