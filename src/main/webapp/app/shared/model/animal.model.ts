import { Moment } from 'moment';
import { IPlanet } from 'app/shared/model/planet.model';
import { AnimalType } from 'app/shared/model/enumerations/animal-type.model';

export interface IAnimal {
  id?: number;
  name?: string;
  animalType?: AnimalType;
  maxHealth?: number;
  currentHealth?: number;
  creationDate?: Moment;
  happiness?: boolean;
  deathNotified?: boolean;
  planet?: IPlanet;
}

export class Animal implements IAnimal {
  constructor(
    public id?: number,
    public name?: string,
    public animalType?: AnimalType,
    public maxHealth?: number,
    public currentHealth?: number,
    public creationDate?: Moment,
    public happiness?: boolean,
    public deathNotified?: boolean,
    public planet?: IPlanet
  ) {
    this.happiness = this.happiness || false;
    this.deathNotified = this.deathNotified || false;
  }
}
