import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OdlukaListComponent } from './odluka-list.component';

describe('OdlukaListComponent', () => {
  let component: OdlukaListComponent;
  let fixture: ComponentFixture<OdlukaListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OdlukaListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OdlukaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
