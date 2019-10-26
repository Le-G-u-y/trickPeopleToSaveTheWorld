import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrickPeopleToSaveTheWorldTestModule } from '../../../test.module';
import { LightingDataComponent } from 'app/entities/lighting-data/lighting-data.component';
import { LightingDataService } from 'app/entities/lighting-data/lighting-data.service';
import { LightingData } from 'app/shared/model/lighting-data.model';

describe('Component Tests', () => {
  describe('LightingData Management Component', () => {
    let comp: LightingDataComponent;
    let fixture: ComponentFixture<LightingDataComponent>;
    let service: LightingDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrickPeopleToSaveTheWorldTestModule],
        declarations: [LightingDataComponent],
        providers: []
      })
        .overrideTemplate(LightingDataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LightingDataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LightingDataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LightingData(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lightingData[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
