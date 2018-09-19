import {Directive, HostListener, Output, EventEmitter} from '@angular/core';

@Directive({
  selector: '[appScroll]',
})
export class ScrollDirective {

  @Output() scrolled = new EventEmitter<any>();

  @HostListener('window:scroll', ['$event'])
  onScroll(event) {
    let endReached = false;
    if (window.innerHeight + window.scrollY === document.body.scrollHeight) {
      endReached = true;
    }
    this.scrolled.emit({ endReached });
  }

}