import { TestBed } from '@angular/core/testing';

import { GradjaninGuard } from './gradjanin.guard';

describe('GradjaninGuard', () => {
  let guard: GradjaninGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(GradjaninGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
