import { TestBed } from '@angular/core/testing';

import { ResenjeService } from './resenje.service';

describe('ResenjeService', () => {
  let service: ResenjeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResenjeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
