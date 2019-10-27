import { EventType } from 'app/shared/model/enumerations/event-type.model';

export interface IEvent {
  id?: number;
  eventType?: EventType;
  eventValue?: string;
}

export class Event implements IEvent {
  constructor(public id?: number, public eventType?: EventType, public eventValue?: string) {}
}
