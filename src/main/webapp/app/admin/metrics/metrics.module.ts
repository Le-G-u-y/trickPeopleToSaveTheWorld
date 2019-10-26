import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TrickPeopleToSaveTheWorldSharedModule } from 'app/shared/shared.module';

import { JhiMetricsMonitoringComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';

@NgModule({
  imports: [TrickPeopleToSaveTheWorldSharedModule, RouterModule.forChild([metricsRoute])],
  declarations: [JhiMetricsMonitoringComponent]
})
export class MetricsModule {}
