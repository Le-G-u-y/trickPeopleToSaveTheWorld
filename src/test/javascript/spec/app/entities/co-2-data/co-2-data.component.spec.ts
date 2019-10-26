import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrickPeopleToSaveTheWorldTestModule } from '../../../test.module';
import { Co2DataComponent } from 'app/entities/co-2-data/co-2-data.component';
import { Co2DataService } from 'app/entities/co-2-data/co-2-data.service';
import { Co2Data } from 'app/shared/model/co-2-data.model';

describe('Component Tests', () => {
  describe('Co2Data Management Component', () => {
    let comp: Co2DataComponent;
    let fixture: ComponentFixture<Co2DataComponent>;
    let service: Co2DataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrickPeopleToSaveTheWorldTestModule],
        declarations: [Co2DataComponent],
        providers: []
      })
        .overrideTemplate(Co2DataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Co2DataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Co2DataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Co2Data(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.co2Data[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
