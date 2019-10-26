import { IPlanet } from 'app/shared/model/planet.model';

export interface IAchievement {
  id?: number;
  name?: string;
  planets?: IPlanet[];
}

export class Achievement implements IAchievement {
  constructor(public id?: number, public name?: string, public planets?: IPlanet[]) {}
}
