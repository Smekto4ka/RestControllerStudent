import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule} from '@angular/common/http';
import {RouterModule , Routes} from '@angular/router';
import { AppComponent } from './app.component';
import { StudentComponent } from './student/student.component';
import {FormsModule} from '@angular/forms';
import { PostStudentComponent } from './post-student/post-student.component';

const appRoutes: Routes = [
  {path : 'listStudent' , component: StudentComponent},
  {path: 'newStudent' , component: PostStudentComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    StudentComponent,
    PostStudentComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
