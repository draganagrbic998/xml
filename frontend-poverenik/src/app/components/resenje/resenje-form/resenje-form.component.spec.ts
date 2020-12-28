import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResenjeFormComponent } from './resenje-form.component';

describe('ResenjeFormComponent', () => {
  let component: ResenjeFormComponent;
  let fixture: ComponentFixture<ResenjeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResenjeFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResenjeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
