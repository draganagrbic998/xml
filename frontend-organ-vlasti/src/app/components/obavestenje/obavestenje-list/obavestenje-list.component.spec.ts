import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ObavestenjeListComponent } from './obavestenje-list.component';

describe('ObavestenjeListComponent', () => {
  let component: ObavestenjeListComponent;
  let fixture: ComponentFixture<ObavestenjeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ObavestenjeListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ObavestenjeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
