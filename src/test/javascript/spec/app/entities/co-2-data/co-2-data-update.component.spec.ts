import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TrickPeopleToSaveTheWorldTestModule } from '../../../test.module';
import { Co2DataUpdateComponent } from 'app/entities/co-2-data/co-2-data-update.component';
import { Co2DataService } from 'app/entities/co-2-data/co-2-data.service';
import { Co2Data } from 'app/shared/model/co-2-data.model';

describe('Component Tests', () => {
  describe('Co2Data Management Update Component', () => {
    let comp: Co2DataUpdateComponent;
    let fixture: ComponentFixture<Co2DataUpdateComponent>;
    let service: Co2DataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrickPeopleToSaveTheWorldTestModule],
        declarations: [Co2DataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(Co2DataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Co2DataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Co2DataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Co2Data(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Co2Data();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
