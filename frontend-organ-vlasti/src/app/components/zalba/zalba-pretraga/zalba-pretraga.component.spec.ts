import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZalbaPretragaComponent } from './zalba-pretraga.component';

describe('ZalbaPretragaComponent', () => {
  let component: ZalbaPretragaComponent;
  let fixture: ComponentFixture<ZalbaPretragaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZalbaPretragaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZalbaPretragaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
