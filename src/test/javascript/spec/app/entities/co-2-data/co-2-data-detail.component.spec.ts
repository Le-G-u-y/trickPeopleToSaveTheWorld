import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrickPeopleToSaveTheWorldTestModule } from '../../../test.module';
import { Co2DataDetailComponent } from 'app/entities/co-2-data/co-2-data-detail.component';
import { Co2Data } from 'app/shared/model/co-2-data.model';

describe('Component Tests', () => {
  describe('Co2Data Management Detail Component', () => {
    let comp: Co2DataDetailComponent;
    let fixture: ComponentFixture<Co2DataDetailComponent>;
    const route = ({ data: of({ co2Data: new Co2Data(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrickPeopleToSaveTheWorldTestModule],
        declarations: [Co2DataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(Co2DataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Co2DataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.co2Data).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
