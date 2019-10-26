import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrickPeopleToSaveTheWorldTestModule } from '../../../test.module';
import { AchievementDetailComponent } from 'app/entities/achievement/achievement-detail.component';
import { Achievement } from 'app/shared/model/achievement.model';

describe('Component Tests', () => {
  describe('Achievement Management Detail Component', () => {
    let comp: AchievementDetailComponent;
    let fixture: ComponentFixture<AchievementDetailComponent>;
    const route = ({ data: of({ achievement: new Achievement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrickPeopleToSaveTheWorldTestModule],
        declarations: [AchievementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AchievementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AchievementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.achievement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
