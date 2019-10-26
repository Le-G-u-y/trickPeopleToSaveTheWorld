import { IAnimal } from 'app/shared/model/animal.model';
import { IAchievement } from 'app/shared/model/achievement.model';

export interface IPlanet {
  id?: number;
  forestPoints?: number;
  waterPoints?: number;
  smogPoints?: number;
  animals?: IAnimal[];
  achievements?: IAchievement[];
}

export class Planet implements IPlanet {
  constructor(
    public id?: number,
    public forestPoints?: number,
    public waterPoints?: number,
    public smogPoints?: number,
    public animals?: IAnimal[],
    public achievements?: IAchievement[]
  ) {}
}
