import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZalbaCutanjeFormComponent } from './zalba-cutanje-form.component';

describe('ZalbaCutanjeFormComponent', () => {
  let component: ZalbaCutanjeFormComponent;
  let fixture: ComponentFixture<ZalbaCutanjeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZalbaCutanjeFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZalbaCutanjeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
