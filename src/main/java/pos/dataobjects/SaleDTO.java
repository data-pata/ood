package pos.dataobjects;

import java.util.List;
    
/**
 * DTO for object of class <code>SaleDTO</code>
 * Encapsulates all data of a sale.
 * @see Sale
 */
public final class SaleDTO {
    private final double totalSum;
    private final List<LineItemDTO> LineItemDTOList;
    private final double totalVAT;
    /**
     * Creates a SaleDTO instance.
     * 
     * @param runningTotal  The runningTotal of the sale.  
     * @param LineItemsDTO  List of LineItemDTO objects.
     * @param totalVAT      The total accumulated VAT amount of the sale.
     */
    public SaleDTO(double runningTotal, List<LineItemDTO> LineItemsDTO, double totalVAT) {
        this.totalSum = runningTotal;
        this.LineItemDTOList = LineItemsDTO;
        this.totalVAT = totalVAT;
    }

    /** 
     * Get the total sum of the sale. 
     * 
     * @return double
     */
    public double getTotalSum() {
        return this.totalSum;
    }

    
    /** 
     * Get the list of LineItemDTO:s
     * 
     * @return List<LineItemDTO>
     */
    public List<LineItemDTO> getLineItemsDTO() {
        return this.LineItemDTOList;
    }

    
    /** 
     * Get the total VAT. 
     * 
     * @return double
     */
    public double getTotalVAT() {
        return this.totalVAT;
    }

}