import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrickPeopleToSaveTheWorldTestModule } from '../../../test.module';
import { LightingDataDetailComponent } from 'app/entities/lighting-data/lighting-data-detail.component';
import { LightingData } from 'app/shared/model/lighting-data.model';

describe('Component Tests', () => {
  describe('LightingData Management Detail Component', () => {
    let comp: LightingDataDetailComponent;
    let fixture: ComponentFixture<LightingDataDetailComponent>;
    const route = ({ data: of({ lightingData: new LightingData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrickPeopleToSaveTheWorldTestModule],
        declarations: [LightingDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LightingDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LightingDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lightingData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
