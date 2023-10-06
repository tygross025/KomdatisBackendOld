import { BuildingWarmWaterDto } from "./building-warm-water-dto";
import { BuildingWarmthDto } from "./building-warmth-dto";

export interface BuildingDto {
  id?: string;
  firstName: string;
  lastName: string;
  address: string;
  livingSpace: number;
  warmth: BuildingWarmthDto[];
  warmWater: BuildingWarmWaterDto[];
  heatedBasement: boolean;
  apartments: number;
}
