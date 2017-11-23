package com.videorentalstore.resources;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.videorentalstore.model.Customer;
import com.videorentalstore.model.Order;
import com.videorentalstore.model.Video;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;
import com.videorentalstore.repository.VideoOrderCorrelationRepository;
import com.videorentalstore.repository.VideoRepository;
import com.videorentalstore.repository.dao.CustomerDAO;
import com.videorentalstore.repository.dao.OrderDAO;
import com.videorentalstore.resources.CustomerResources;
import com.videorentalstore.resources.OrderResources;
import com.videorentalstore.resources.VideoResources;

import io.dropwizard.testing.junit.ResourceTestRule;

@SuppressWarnings("unchecked")
public class ResourcesTest {
	
    private static final VideoRepository videoRepository = mock(VideoRepository.class);
    private static final OrderRepository orderRepository = mock(OrderRepository.class);
    private static final CustomerRepository customerRepository = mock(CustomerRepository.class);
    private static final VideoOrderCorrelationRepository videoOrderCorrelationRepository = mock(VideoOrderCorrelationRepository.class);
    private Client client;
	private OrderDAO orderDao = mock(OrderDAO.class);
	private CustomerDAO dao = mock(CustomerDAO.class);
	private ArrayList<Order> orderList = mock(ArrayList.class);
	private ArrayList<Video> listOfVideos = mock(ArrayList.class);
	
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new VideoResources(videoRepository))
            .addResource(new OrderResources(orderRepository, customerRepository, videoOrderCorrelationRepository, videoRepository))
            .addResource(new CustomerResources(orderRepository, customerRepository))
            .build();
    
    @Before
    public void setUp() {
        client = resources.client();
        
    	when(dao.getCustomerById(anyLong())).thenReturn(new Customer(0, 0));
    	when(customerRepository.getCustomerById(anyLong())).thenReturn(Optional.ofNullable(new Customer(0, 0)));
    	
    	when(orderList.size()).thenReturn(3);

    	when(orderDao.getOrdersByCustomerId(anyLong())).thenReturn(orderList);
    	when(orderRepository.getOrdersByCustomerId(anyLong())).thenReturn(orderList);
    	
    	Video mockVideo = new Video(5, "Mock123", "Mock release");
    	listOfVideos.add(mockVideo);
    	when(videoRepository.getAllVideos()).thenReturn(listOfVideos);
    }
    
    @Test
    public void testAddNewVideo() {
        Entity<?> videoEntity;
    	Video video = new Video(6, "Terminator 2", "Old film");
    	videoEntity = Entity.entity(video, MediaType.APPLICATION_JSON_TYPE);
        Response addNewVideoResponse = client.target(VideoResources.PATH)
        		.path(VideoResources.ADD_NEW_VIDEO)
                .request()
                .post(videoEntity);

        assertEquals(Response.Status.OK, addNewVideoResponse.getStatusInfo());
    }
    
    @Test
    public void testGetOrdersByCustomer() {
        Response testGetOrdersByCustomerResponse = client.target(CustomerResources.PATH)
                .path(CustomerResources.GET_ORDERS_BY_CUSTOMER_ID)
                .resolveTemplate("customerId", 1)
        		.request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(Response.Status.OK, testGetOrdersByCustomerResponse.getStatusInfo());
    }
    
    @Test
    public void testGetAllVideos() {
        Response testGetOrdersByCustomerResponse = client.target(VideoResources.PATH)
                .path(VideoResources.GET_ALL_VIDEOS)
        		.request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(Response.Status.OK, testGetOrdersByCustomerResponse.getStatusInfo());
    }
}
