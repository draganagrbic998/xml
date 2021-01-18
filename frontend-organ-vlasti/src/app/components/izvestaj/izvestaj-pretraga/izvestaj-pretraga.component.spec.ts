import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IzvestajPretragaComponent } from './izvestaj-pretraga.component';

describe('IzvestajPretragaComponent', () => {
  let component: IzvestajPretragaComponent;
  let fixture: ComponentFixture<IzvestajPretragaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IzvestajPretragaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IzvestajPretragaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
