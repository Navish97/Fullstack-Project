import { mount } from '@vue/test-utils';
import PaginationComponent from '@/components/Items/PaginationComponent.vue';
import {expect, it, vi} from "vitest";

  const pages = [1, 2, 3, 4, 5];
  const currentPage = 1;

  it('emits load-page event when a page link is clicked', async () => {
    const wrapper = mount(PaginationComponent, {
      props: {
        pages,
        currentPage,
      },
    });
    await wrapper.find('a[href="#page=3"]').trigger('click');
    expect(wrapper.emitted('load-page')).toBeTruthy();
    expect(wrapper.emitted('load-page')![0]).toEqual([3]);
  });

  it('emits previous-page event when the previous button is clicked', async () => {
    const wrapper = mount(PaginationComponent, {
      props: {
        pages,
        currentPage:5,
      },
    });
    await wrapper.find('.pagination a:first-child').trigger('click');
    expect(wrapper.emitted('previous-page')).toBeTruthy();
    expect(wrapper.emitted('previous-page')![0]).toEqual([-1]);
  });

  it('emits previous-page event when the next button is clicked', async () => {
    const wrapper = mount(PaginationComponent, {
      props: {
        pages,
        currentPage: 1,
      },
    });
    await wrapper.find('.pagination a:last-child').trigger('click');
    expect(wrapper.emitted('previous-page')).toBeTruthy();
    expect(wrapper.emitted('previous-page')![0]).toEqual([1]);
  });

  it('disables the previous button when the current page is 1', () => {
    const wrapper = mount(PaginationComponent, {
      props: {
        pages,
        currentPage:1,
      },
    });
    const prevBtn = wrapper.find('.pagination a:first-child');
    expect(prevBtn.classes()).toContain('disabled');
  });

  it('disables the next button when the current page is the last page', () => {
    const wrapper = mount(PaginationComponent, {
      props: {
        pages,
        currentPage: 5,
      },
    });
    const nextBtn = wrapper.find('.pagination a:last-child');
    expect(nextBtn.classes()).toContain('disabled');
  });

  it('shows <... when the number of pages exceeds limit and current page is on the top half', () => {
    const wrapper = mount(PaginationComponent, {
      props: {
        pages: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18],
        currentPage: 17,
      },
    });
    expect(wrapper.find('.pagination a:nth-child(3)').text()).toBe('...');
  });

  it('shows only 3 pages when the window inner width is less than 768px', () => {
    Object.defineProperty(window, 'innerWidth', { value: 767 });
    const wrapper = mount(PaginationComponent, {
      props: {
        pages,
        currentPage,
      },
    });
    console.log(wrapper.findAll('.pagination a'));
    expect(wrapper.findAll('.pagination a')).toHaveLength(6); //6 due to navigation buttons and ...
  });