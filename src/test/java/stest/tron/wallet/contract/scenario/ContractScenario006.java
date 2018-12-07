package stest.tron.wallet.contract.scenario;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.tron.api.GrpcAPI.AccountResourceMessage;
import org.tron.api.WalletGrpc;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Utils;
import org.tron.core.Wallet;
import org.tron.protos.Protocol.SmartContract;
import stest.tron.wallet.common.client.Configuration;
import stest.tron.wallet.common.client.Parameter.CommonConstant;
import stest.tron.wallet.common.client.utils.PublicMethed;

@Slf4j
public class ContractScenario006 {

  private final String testKey002 = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private final byte[] fromAddress = PublicMethed.getFinalAddress(testKey002);

  private ManagedChannel channelFull = null;
  private WalletGrpc.WalletBlockingStub blockingStubFull = null;
  private String fullnode = Configuration.getByPath("testng.conf")
      .getStringList("fullnode.ip.list").get(0);
  private Long maxFeeLimit = Configuration.getByPath("testng.conf")
      .getLong("defaultParameter.maxFeeLimit");

  ECKey ecKey1 = new ECKey(Utils.getRandom());
  byte[] contract006Address = ecKey1.getAddress();
  String contract006Key = ByteArray.toHexString(ecKey1.getPrivKeyBytes());

  @BeforeSuite
  public void beforeSuite() {
    Wallet wallet = new Wallet();
    Wallet.setAddressPreFixByte(CommonConstant.ADD_PRE_FIX_BYTE_MAINNET);
  }

  @BeforeClass(enabled = true)
  public void beforeClass() {
    channelFull = ManagedChannelBuilder.forTarget(fullnode)
        .usePlaintext(true)
        .build();
    blockingStubFull = WalletGrpc.newBlockingStub(channelFull);
  }

  @Test(enabled = true)
  public void deployFomo3D() {
    ecKey1 = new ECKey(Utils.getRandom());
    contract006Address = ecKey1.getAddress();
    contract006Key = ByteArray.toHexString(ecKey1.getPrivKeyBytes());
    PublicMethed.printAddress(contract006Key);

    Assert.assertTrue(PublicMethed.sendcoin(contract006Address,200000000L,fromAddress,
        testKey002,blockingStubFull));
    logger.info(Long.toString(PublicMethed.queryAccount(contract006Key,blockingStubFull)
        .getBalance()));
    Assert.assertTrue(PublicMethed.freezeBalanceGetEnergy(contract006Address, 10000000L,
        3,1,contract006Key,blockingStubFull));
    AccountResourceMessage accountResource = PublicMethed.getAccountResource(contract006Address,
        blockingStubFull);
    Long energyLimit = accountResource.getEnergyLimit();
    Long energyUsage = accountResource.getEnergyUsed();

    logger.info("before energy limit is " + Long.toString(energyLimit));
    logger.info("before energy usage is " + Long.toString(energyUsage));
    String contractName = "Fomo3D";
    String code = "60c0604052600660808190527f464f4d4f3344000000000000000000000000000000000000000000000000000060a0908152620000409160009190620000b8565b506040805180820190915260038082527f463344000000000000000000000000000000000000000000000000000000000060209092019182526200008791600191620000b8565b5068056bc75e2d631000006002556000600855600b805460ff19169055348015620000b157600080fd5b506200015d565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000fb57805160ff19168380011785556200012b565b828001600101855582156200012b579182015b828111156200012b5782518255916020019190600101906200010e565b50620001399291506200013d565b5090565b6200015a91905b8082111562000139576000815560010162000144565b90565b611688806200016d6000396000f3006080604052600436106101685763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166265318b811461017657806306fdde03146101a957806310d0ffdd1461023357806318160ddd1461024b578063226093731461026057806327defa1f14610278578063313ce567146102a1578063392efb52146102cc5780633ccfd60b146102e45780634b750334146102fb57806356d399e814610310578063688abbf7146103255780636b2f46321461033f57806370a08231146103545780638328b610146103755780638620410b1461038d57806389135ae9146103a25780638fea64bd146103bf578063949e8acd146103d457806395d89b41146103e9578063a8e04f34146103fe578063a9059cbb14610413578063b84c824614610437578063c47f002714610490578063e4849b32146104e9578063e9fad8ee14610501578063f088d54714610516578063fdb5a03e1461052a575b61017334600061053f565b50005b34801561018257600080fd5b50610197600160a060020a0360043516610b15565b60408051918252519081900360200190f35b3480156101b557600080fd5b506101be610b50565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101f85781810151838201526020016101e0565b50505050905090810190601f1680156102255780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561023f57600080fd5b50610197600435610bde565b34801561025757600080fd5b50610197610c0e565b34801561026c57600080fd5b50610197600435610c15565b34801561028457600080fd5b5061028d610c4e565b604080519115158252519081900360200190f35b3480156102ad57600080fd5b506102b6610c57565b6040805160ff9092168252519081900360200190f35b3480156102d857600080fd5b5061028d600435610c5c565b3480156102f057600080fd5b506102f9610c71565b005b34801561030757600080fd5b50610197610d44565b34801561031c57600080fd5b50610197610d98565b34801561033157600080fd5b506101976004351515610d9e565b34801561034b57600080fd5b50610197610de1565b34801561036057600080fd5b50610197600160a060020a0360043516610de6565b34801561038157600080fd5b506102f9600435610e01565b34801561039957600080fd5b50610197610e48565b3480156103ae57600080fd5b506102f96004356024351515610e90565b3480156103cb57600080fd5b506102f9610ef2565b3480156103e057600080fd5b50610197610ef4565b3480156103f557600080fd5b506101be610f07565b34801561040a57600080fd5b506102f9610f61565b34801561041f57600080fd5b5061028d600160a060020a0360043516602435610faf565b34801561044357600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526102f99436949293602493928401919081908401838280828437509497506111699650505050505050565b34801561049c57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526102f99436949293602493928401919081908401838280828437509497506111c29650505050505050565b3480156104f557600080fd5b506102f9600435611216565b34801561050d57600080fd5b506102f9611367565b610197600160a060020a0360043516611394565b34801561053657600080fd5b506102f96113a0565b60008060008060008060008060008a6000339050600b60009054906101000a900460ff16801561058157506801158e460913d000008261057d610de1565b0311155b1561088e57600160a060020a03811660009081526003602052604090205460ff16151560011480156105d65750600160a060020a038116600090815260076020526040902054670de0b6b3a764000090830111155b15156105e157600080fd5b600160a060020a0381166000908152600760205260409020546106049083611456565b600160a060020a03821660009081526007602052604090205533995061062b8d600561146c565b985061063889600361146c565b97506106448989611483565b96506106508d8a611483565b955061065b86611495565b9450680100000000000000008702935060008511801561068557506008546106838682611456565b115b151561069057600080fd5b600160a060020a038c16158015906106ba575089600160a060020a03168c600160a060020a031614155b80156106e05750600254600160a060020a038d1660009081526004602052604090205410155b1561072657600160a060020a038c166000908152600560205260409020546107089089611456565b600160a060020a038d16600090815260056020526040902055610741565b6107308789611456565b965068010000000000000000870293505b600060085411156107a55761075860085486611456565b600881905568010000000000000000880281151561077257fe5b6009805492909104909101905560085468010000000000000000880281151561079757fe5b0485028403840393506107ab565b60088590555b600160a060020a038a166000908152600460205260409020546107ce9086611456565b600460008c600160a060020a0316600160a060020a031681526020019081526020016000208190555083856009540203925082600660008c600160a060020a0316600160a060020a03168152602001908152602001600020600082825401925050819055508b600160a060020a03168a600160a060020a03167f022c0d992e4d873a3748436d960d5140c1f9721cf73f7ca5ec679d3d9f4fe2d58f88604051808381526020018281526020019250505060405180910390a3849a50610b05565b600b805460ff191690553399506108a68d600561146c565b98506108b389600361146c565b97506108bf8989611483565b96506108cb8d8a611483565b95506108d686611495565b9450680100000000000000008702935060008511801561090057506008546108fe8682611456565b115b151561090b57600080fd5b600160a060020a038c1615801590610935575089600160a060020a03168c600160a060020a031614155b801561095b5750600254600160a060020a038d1660009081526004602052604090205410155b156109a157600160a060020a038c166000908152600560205260409020546109839089611456565b600160a060020a038d166000908152600560205260409020556109bc565b6109ab8789611456565b965068010000000000000000870293505b60006008541115610a20576109d360085486611456565b60088190556801000000000000000088028115156109ed57fe5b60098054929091049091019055600854680100000000000000008802811515610a1257fe5b048502840384039350610a26565b60088590555b600160a060020a038a16600090815260046020526040902054610a499086611456565b600460008c600160a060020a0316600160a060020a031681526020019081526020016000208190555083856009540203925082600660008c600160a060020a0316600160a060020a03168152602001908152602001600020600082825401925050819055508b600160a060020a03168a600160a060020a03167f022c0d992e4d873a3748436d960d5140c1f9721cf73f7ca5ec679d3d9f4fe2d58f88604051808381526020018281526020019250505060405180910390a3849a505b5050505050505050505092915050565b600160a060020a0316600090815260066020908152604080832054600490925290912054600954680100000000000000009102919091030490565b6000805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610bd65780601f10610bab57610100808354040283529160200191610bd6565b820191906000526020600020905b815481529060010190602001808311610bb957829003601f168201915b505050505081565b6000808080610bee85600561146c565b9250610bfa8584611483565b9150610c0582611495565b95945050505050565b6008545b90565b6000806000806008548511151515610c2c57600080fd5b610c358561152d565b9250610c4283600561146c565b9150610c058383611483565b600b5460ff1681565b601281565b600a6020526000908152604090205460ff1681565b6000806000610c806001610d9e565b11610c8a57600080fd5b339150610c976000610d9e565b600160a060020a038316600081815260066020908152604080832080546801000000000000000087020190556005909152808220805490839055905193019350909183156108fc0291849190818181858888f19350505050158015610d00573d6000803e3d6000fd5b50604080518281529051600160a060020a038416917fccad973dcd043c7d680389db4378bd6b9775db7124092e9e0422c9e46d7985dc919081900360200190a25050565b60008060008060085460001415610d62576414f46b04009350610d92565b610d73670de0b6b3a764000061152d565b9250610d8083600561146c565b9150610d8c8383611483565b90508093505b50505090565b60025481565b60003382610db457610daf81610b15565b610dd8565b600160a060020a038116600090815260056020526040902054610dd682610b15565b015b91505b50919050565b303190565b600160a060020a031660009081526004602052604090205490565b604080516c010000000000000000000000003390810282528251918290036014019091206000908152600a602052919091205460ff161515610e4257600080fd5b50600255565b60008060008060085460001415610e665764199c82cc009350610d92565b610e77670de0b6b3a764000061152d565b9250610e8483600561146c565b9150610d8c8383611456565b604080516c010000000000000000000000003390810282528251918290036014019091206000908152600a602052919091205460ff161515610ed157600080fd5b506000918252600a6020526040909120805460ff1916911515919091179055565b565b600033610f0081610de6565b91505b5090565b60018054604080516020600284861615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610bd65780601f10610bab57610100808354040283529160200191610bd6565b604080516c010000000000000000000000003390810282528251918290036014019091206000908152600a602052919091205460ff161515610fa257600080fd5b50600b805460ff19169055565b600080600080600080610fc0610ef4565b11610fca57600080fd5b600b5433945060ff16158015610ff85750600160a060020a0384166000908152600460205260409020548611155b151561100357600080fd5b600061100f6001610d9e565b111561101d5761101d610c71565b61102886600561146c565b92506110348684611483565b915061103f8361152d565b905061104d60085484611483565b600855600160a060020a0384166000908152600460205260409020546110739087611483565b600160a060020a0380861660009081526004602052604080822093909355908916815220546110a29083611456565b600160a060020a0388811660008181526004602090815260408083209590955560098054948a16835260069091528482208054948c02909403909355825491815292909220805492850290920190915554600854611116919068010000000000000000840281151561111057fe5b04611456565b600955604080518381529051600160a060020a03808a1692908716917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9181900360200190a35060019695505050505050565b604080516c010000000000000000000000003390810282528251918290036014019091206000908152600a602052919091205460ff1615156111aa57600080fd5b81516111bd9060019060208501906115ce565b505050565b604080516c010000000000000000000000003390810282528251918290036014019091206000908152600a602052919091205460ff16151561120357600080fd5b81516111bd9060009060208501906115ce565b6000806000806000806000611229610ef4565b1161123357600080fd5b3360008181526004602052604090205490965087111561125257600080fd5b86945061125e8561152d565b935061126b84600561146c565b92506112778484611483565b915061128560085486611483565b600855600160a060020a0386166000908152600460205260409020546112ab9086611483565b600160a060020a0387166000908152600460209081526040808320939093556009546006909152918120805492880268010000000000000000860201928390039055600854919250101561131b5761131760095460085468010000000000000000860281151561111057fe5b6009555b60408051868152602081018490528151600160a060020a038916927fc4823739c5787d2ca17e404aa47d5569ae71dfb49cbf21b3f6152ed238a31139928290030190a250505050505050565b33600081815260046020526040812054908111156113885761138881611216565b611390610c71565b5050565b6000610ddb348361053f565b6000806000806113b06001610d9e565b116113ba57600080fd5b6113c46000610d9e565b3360008181526006602090815260408083208054680100000000000000008702019055600590915281208054908290559092019450925061140690849061053f565b905081600160a060020a03167fbe339fc14b041c2b0e0f3dd2cd325d0c3668b78378001e53160eab36153264588483604051808381526020018281526020019250505060405180910390a2505050565b60008282018381101561146557fe5b9392505050565b600080828481151561147a57fe5b04949350505050565b60008282111561148f57fe5b50900390565b6008546000906c01431e0fae6d7217caa00000009082906402540be40061151a611514730380d4bd8a8678c1bb542c80deb4800000000000880268056bc75e2d631000006002860a02017005e0a1fd2712875988becaad0000000000850201780197d4df19d605767337e9f14d3eec8920e40000000000000001611599565b85611483565b81151561152357fe5b0403949350505050565b600854600090670de0b6b3a76400008381019181019083906115866414f46b04008285046402540be40002018702600283670de0b6b3a763ffff1982890a8b900301046402540be4000281151561158057fe5b04611483565b81151561158f57fe5b0495945050505050565b80600260018201045b81811015610ddb5780915060028182858115156115bb57fe5b04018115156115c657fe5b0490506115a2565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061160f57805160ff191683800117855561163c565b8280016001018555821561163c579182015b8281111561163c578251825591602001919060010190611621565b50610f0392610c129250905b80821115610f0357600081556001016116485600a165627a7a723058202986f1ef7924e33e9f5613300f260c5fd9f05700de3217343d70f3a5fea5f76d0029";
    String abi = "[{\"constant\":true,\"inputs\":[{\"name\":\"_customerAddress\",\"type\":\"address\"}],\"name\":\"dividendsOf\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_ethereumToSpend\",\"type\":\"uint256\"}],\"name\":\"calculateTokensReceived\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_tokensToSell\",\"type\":\"uint256\"}],\"name\":\"calculateEthereumReceived\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"onlyAmbassadors\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"administrators\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"withdraw\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"sellPrice\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"stakingRequirement\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_includeReferralBonus\",\"type\":\"bool\"}],\"name\":\"myDividends\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"totalEthereumBalance\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_customerAddress\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_amountOfTokens\",\"type\":\"uint256\"}],\"name\":\"setStakingRequirement\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"buyPrice\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_identifier\",\"type\":\"bytes32\"},{\"name\":\"_status\",\"type\":\"bool\"}],\"name\":\"setAdministrator\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"Hourglass\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"myTokens\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"disableInitialStage\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_toAddress\",\"type\":\"address\"},{\"name\":\"_amountOfTokens\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_symbol\",\"type\":\"string\"}],\"name\":\"setSymbol\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_name\",\"type\":\"string\"}],\"name\":\"setName\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_amountOfTokens\",\"type\":\"uint256\"}],\"name\":\"sell\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"exit\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_referredBy\",\"type\":\"address\"}],\"name\":\"buy\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"reinvest\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"fallback\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"customerAddress\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"incomingEthereum\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"tokensMinted\",\"type\":\"uint256\"},{\"indexed\":true,\"name\":\"referredBy\",\"type\":\"address\"}],\"name\":\"onTokenPurchase\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"customerAddress\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"tokensBurned\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"ethereumEarned\",\"type\":\"uint256\"}],\"name\":\"onTokenSell\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"customerAddress\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"ethereumReinvested\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"tokensMinted\",\"type\":\"uint256\"}],\"name\":\"onReinvestment\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"customerAddress\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"ethereumWithdrawn\",\"type\":\"uint256\"}],\"name\":\"onWithdraw\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"tokens\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"}]";
    byte[] contractAddress = PublicMethed.deployContract(contractName,abi,code,"",maxFeeLimit,
        0L,100,null, contract006Key,contract006Address,blockingStubFull);
    SmartContract smartContract = PublicMethed.getContract(contractAddress,blockingStubFull);
    Assert.assertFalse(smartContract.getAbi().toString().isEmpty());
    Assert.assertTrue(smartContract.getName().equalsIgnoreCase(contractName));
    Assert.assertFalse(smartContract.getBytecode().toString().isEmpty());
    accountResource = PublicMethed.getAccountResource(contract006Address,blockingStubFull);
    energyLimit = accountResource.getEnergyLimit();
    energyUsage = accountResource.getEnergyUsed();
    Assert.assertTrue(energyLimit > 0);
    Assert.assertTrue(energyUsage > 0);
    logger.info("after energy limit is " + Long.toString(energyLimit));
    logger.info("after energy usage is " + Long.toString(energyUsage));
  }

  @AfterClass
  public void shutdown() throws InterruptedException {
    if (channelFull != null) {
      channelFull.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}

