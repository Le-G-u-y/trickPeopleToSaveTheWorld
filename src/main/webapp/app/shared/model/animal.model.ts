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
    public planet?: IPlanet
  ) {
    this.happiness = this.happiness || false;
  }
}
