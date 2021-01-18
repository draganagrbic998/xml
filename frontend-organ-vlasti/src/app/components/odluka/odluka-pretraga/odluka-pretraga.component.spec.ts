import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OdlukaPretragaComponent } from './odluka-pretraga.component';

describe('OdlukaPretragaComponent', () => {
  let component: OdlukaPretragaComponent;
  let fixture: ComponentFixture<OdlukaPretragaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OdlukaPretragaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OdlukaPretragaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
