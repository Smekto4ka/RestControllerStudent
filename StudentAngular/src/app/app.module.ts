import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {StudentComponent} from './student/student.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PostStudentComponent} from './post-student/post-student.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSelectModule} from '@angular/material/select';
import {MatExpansionModule} from '@angular/material/expansion';
import {StudentFilterPipe} from './shared/service/student-filter.pipe';

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
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatSelectModule,
    MatExpansionModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
