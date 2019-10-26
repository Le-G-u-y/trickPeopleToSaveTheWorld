export interface ILightingData {
  id?: number;
  onSeconds?: number;
  offSeconds?: number;
}

export class LightingData implements ILightingData {
  constructor(public id?: number, public onSeconds?: number, public offSeconds?: number) {}
}
