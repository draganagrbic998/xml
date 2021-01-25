import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ObicnaPretragaComponent } from './obicna-pretraga.component';

describe('ObicnaPretragaComponent', () => {
  let component: ObicnaPretragaComponent;
  let fixture: ComponentFixture<ObicnaPretragaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ObicnaPretragaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ObicnaPretragaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
