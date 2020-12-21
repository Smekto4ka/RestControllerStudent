import {BrowserModule} from '@angular/platform-browser';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {StudentComponent} from './student/student.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PostStudentComponent} from './post-student/post-student.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {StudentFilterPipe} from './shared/service/student-filter.pipe';

import {MatExpansionModule} from '@angular/material/expansion';
import {MatSelectModule} from '@angular/material/select';

import {MatPaginatorModule} from '@angular/material/paginator';
import {NgxPaginationModule} from 'ngx-pagination';
import {MatFormFieldModule} from '@angular/material/form-field';



const appRoutes: Routes = [
  {path: 'listStudent', component: StudentComponent},
  {path: 'newStudent', component: PostStudentComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    StudentComponent,
    PostStudentComponent,
    StudentFilterPipe
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatSelectModule,
    MatExpansionModule,
    MatPaginatorModule,
    NgxPaginationModule,
    MatFormFieldModule
  ],

  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule {
}
