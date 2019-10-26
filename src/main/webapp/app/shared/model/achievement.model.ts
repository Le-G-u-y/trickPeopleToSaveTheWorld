export interface IAchievement {
  id?: number;
  name?: string;
}

export class Achievement implements IAchievement {
  constructor(public id?: number, public name?: string) {}
}
