import { Moment } from 'moment';

export interface ICo2Data {
  id?: number;
  timestamp?: Moment;
  co2Value?: number;
  temp?: number;
  humidity?: number;
}

export class Co2Data implements ICo2Data {
  constructor(public id?: number, public timestamp?: Moment, public co2Value?: number, public temp?: number, public humidity?: number) {}
}
